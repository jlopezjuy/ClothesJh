import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pago } from './pago.model';
import { PagoPopupService } from './pago-popup.service';
import { PagoService } from './pago.service';

@Component({
    selector: 'jhi-pago-delete-dialog',
    templateUrl: './pago-delete-dialog.component.html'
})
export class PagoDeleteDialogComponent {

    pago: Pago;

    constructor(
        private pagoService: PagoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pagoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pagoListModification',
                content: 'Deleted an pago'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pago-delete-popup',
    template: ''
})
export class PagoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pagoPopupService: PagoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pagoPopupService
                .open(PagoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
