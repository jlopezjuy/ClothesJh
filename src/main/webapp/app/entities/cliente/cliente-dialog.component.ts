import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Cliente } from './cliente.model';
import { ClientePopupService } from './cliente-popup.service';
import { ClienteService } from './cliente.service';

@Component({
    selector: 'jhi-cliente-dialog',
    templateUrl: './cliente-dialog.component.html'
})
export class ClienteDialogComponent implements OnInit {

    cliente: Cliente;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clienteService: ClienteService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clienteService.update(this.cliente), false);
        } else {
            this.subscribeToSaveResponse(
                this.clienteService.create(this.cliente), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Cliente>, isCreated: boolean) {
        result.subscribe((res: Cliente) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Cliente, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.cliente.created'
            : 'clothesApp.cliente.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clienteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-cliente-popup',
    template: ''
})
export class ClientePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientePopupService: ClientePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientePopupService
                    .open(ClienteDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientePopupService
                    .open(ClienteDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
