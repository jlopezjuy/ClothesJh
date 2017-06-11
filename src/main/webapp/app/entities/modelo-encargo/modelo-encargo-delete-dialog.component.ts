import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { ModeloEncargo } from './modelo-encargo.model';
import { ModeloEncargoPopupService } from './modelo-encargo-popup.service';
import { ModeloEncargoService } from './modelo-encargo.service';

@Component({
    selector: 'jhi-modelo-encargo-delete-dialog',
    templateUrl: './modelo-encargo-delete-dialog.component.html'
})
export class ModeloEncargoDeleteDialogComponent {

    modeloEncargo: ModeloEncargo;

    constructor(
        private modeloEncargoService: ModeloEncargoService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modeloEncargoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'modeloEncargoListModification',
                content: 'Deleted an modeloEncargo'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('clothesApp.modeloEncargo.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-modelo-encargo-delete-popup',
    template: ''
})
export class ModeloEncargoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloEncargoPopupService: ModeloEncargoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.modeloEncargoPopupService
                .open(ModeloEncargoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
