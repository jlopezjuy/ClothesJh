import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Proveedor } from './proveedor.model';
import { ProveedorPopupService } from './proveedor-popup.service';
import { ProveedorService } from './proveedor.service';
import { Rubro, RubroService } from '../rubro';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-proveedor-dialog',
    templateUrl: './proveedor-dialog.component.html'
})
export class ProveedorDialogComponent implements OnInit {

    proveedor: Proveedor;
    authorities: any[];
    isSaving: boolean;

    rubros: Rubro[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private proveedorService: ProveedorService,
        private rubroService: RubroService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.rubroService.query()
            .subscribe((res: ResponseWrapper) => { this.rubros = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.proveedor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.proveedorService.update(this.proveedor), false);
        } else {
            this.subscribeToSaveResponse(
                this.proveedorService.create(this.proveedor), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Proveedor>, isCreated: boolean) {
        result.subscribe((res: Proveedor) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Proveedor, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.proveedor.created'
            : 'clothesApp.proveedor.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'proveedorListModification', content: 'OK'});
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

    trackRubroById(index: number, item: Rubro) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-proveedor-popup',
    template: ''
})
export class ProveedorPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private proveedorPopupService: ProveedorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.proveedorPopupService
                    .open(ProveedorDialogComponent, params['id']);
            } else {
                this.modalRef = this.proveedorPopupService
                    .open(ProveedorDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
