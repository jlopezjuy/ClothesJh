import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Modelo } from './modelo.model';
import { ModeloPopupService } from './modelo-popup.service';
import { ModeloService } from './modelo.service';

@Component({
    selector: 'jhi-modelo-delete-dialog',
    templateUrl: './modelo-delete-dialog.component.html'
})
export class ModeloDeleteDialogComponent {

    modelo: Modelo;

    constructor(
        private modeloService: ModeloService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modeloService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'modeloListModification',
                content: 'Deleted an modelo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-modelo-delete-popup',
    template: ''
})
export class ModeloDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloPopupService: ModeloPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.modeloPopupService
                .open(ModeloDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
