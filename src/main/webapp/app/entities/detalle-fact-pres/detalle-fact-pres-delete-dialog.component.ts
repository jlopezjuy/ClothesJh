import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { DetalleFactPres } from './detalle-fact-pres.model';
import { DetalleFactPresPopupService } from './detalle-fact-pres-popup.service';
import { DetalleFactPresService } from './detalle-fact-pres.service';

@Component({
    selector: 'jhi-detalle-fact-pres-delete-dialog',
    templateUrl: './detalle-fact-pres-delete-dialog.component.html'
})
export class DetalleFactPresDeleteDialogComponent {

    detalleFactPres: DetalleFactPres;

    constructor(
        private detalleFactPresService: DetalleFactPresService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleFactPresService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detalleFactPresListModification',
                content: 'Deleted an detalleFactPres'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('clothesApp.detalleFactPres.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-detalle-fact-pres-delete-popup',
    template: ''
})
export class DetalleFactPresDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleFactPresPopupService: DetalleFactPresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.detalleFactPresPopupService
                .open(DetalleFactPresDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
