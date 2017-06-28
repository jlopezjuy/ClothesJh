import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Encargo } from './encargo.model';
import { EncargoPopupService } from './encargo-popup.service';
import { EncargoService } from './encargo.service';

@Component({
    selector: 'jhi-encargo-delete-dialog',
    templateUrl: './encargo-delete-dialog.component.html'
})
export class EncargoDeleteDialogComponent {

    encargo: Encargo;

    constructor(
        private encargoService: EncargoService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.encargoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'encargoListModification',
                content: 'Deleted an encargo'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('clothesApp.encargo.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-encargo-delete-popup',
    template: ''
})
export class EncargoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private encargoPopupService: EncargoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.encargoPopupService
                .open(EncargoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
