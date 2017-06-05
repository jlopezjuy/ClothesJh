import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { Modelo } from './modelo.model';
import { ModeloPopupService } from './modelo-popup.service';
import { ModeloService } from './modelo.service';
import { Cliente, ClienteService } from '../cliente';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-modelo-dialog',
    templateUrl: './modelo-dialog.component.html'
})
export class ModeloDialogComponent implements OnInit {

    modelo: Modelo;
    authorities: any[];
    isSaving: boolean;

    clientes: Cliente[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private activatedRoute: ActivatedRoute,
        private modeloService: ModeloService,
        private clienteService: ClienteService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clienteService.query()
            .subscribe((res: ResponseWrapper) => { this.clientes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.activatedRoute.params.forEach((params:Params) => {
            let idCliente = params['clienteId'];
            console.log("Cliente id: " + idCliente);
            this.modelo.clienteId = idCliente;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, modelo, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                modelo[field] = base64Data;
                modelo[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.modelo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeloService.update(this.modelo));
        } else {
            this.subscribeToSaveResponse(
                this.modeloService.create(this.modelo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Modelo>) {
        result.subscribe((res: Modelo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Modelo) {
        this.eventManager.broadcast({ name: 'modeloListModification', content: 'OK'});
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
    selector: 'jhi-modelo-popup',
    template: ''
})
export class ModeloPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloPopupService: ModeloPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.modeloPopupService
                    .open(ModeloDialogComponent, params['id']);
            } else {
                this.modalRef = this.modeloPopupService
                    .open(ModeloDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
