import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Medida } from './medida.model';
import { MedidaPopupService } from './medida-popup.service';
import { MedidaService } from './medida.service';
import { Cliente, ClienteService } from '../cliente';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-medida-dialog',
    templateUrl: './medida-dialog.component.html'
})
export class MedidaDialogComponent implements OnInit {

    medida: Medida;
    authorities: any[];
    isSaving: boolean;

    clientes: Cliente[];
    fechaMedidaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private medidaService: MedidaService,
        private clienteService: ClienteService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clienteService.query()
            .subscribe((res: ResponseWrapper) => { this.clientes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medida.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medidaService.update(this.medida));
        } else {
            this.subscribeToSaveResponse(
                this.medidaService.create(this.medida));
        }
    }

    private subscribeToSaveResponse(result: Observable<Medida>) {
        result.subscribe((res: Medida) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Medida) {
        this.eventManager.broadcast({ name: 'medidaListModification', content: 'OK'});
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

    trackClienteById(index: number, item: Cliente) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-medida-popup',
    template: ''
})
export class MedidaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medidaPopupService: MedidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.medidaPopupService
                    .open(MedidaDialogComponent, params['id']);
            } else {
                this.modalRef = this.medidaPopupService
                    .open(MedidaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
