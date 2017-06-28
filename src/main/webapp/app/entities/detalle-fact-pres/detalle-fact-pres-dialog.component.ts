import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetalleFactPres } from './detalle-fact-pres.model';
import { DetalleFactPresPopupService } from './detalle-fact-pres-popup.service';
import { DetalleFactPresService } from './detalle-fact-pres.service';
import { FacturaPresupuesto, FacturaPresupuestoService } from '../factura-presupuesto';
import { Producto, ProductoService } from '../producto';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-detalle-fact-pres-dialog',
    templateUrl: './detalle-fact-pres-dialog.component.html'
})
export class DetalleFactPresDialogComponent implements OnInit {

    detalleFactPres: DetalleFactPres;
    authorities: any[];
    isSaving: boolean;

    facturapresupuestos: FacturaPresupuesto[];

    productos: Producto[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private detalleFactPresService: DetalleFactPresService,
        private facturaPresupuestoService: FacturaPresupuestoService,
        private productoService: ProductoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facturaPresupuestoService.query()
            .subscribe((res: ResponseWrapper) => { this.facturapresupuestos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.productoService.query()
            .subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detalleFactPres.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detalleFactPresService.update(this.detalleFactPres), false);
        } else {
            this.subscribeToSaveResponse(
                this.detalleFactPresService.create(this.detalleFactPres), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<DetalleFactPres>, isCreated: boolean) {
        result.subscribe((res: DetalleFactPres) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DetalleFactPres, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.detalleFactPres.created'
            : 'clothesApp.detalleFactPres.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'detalleFactPresListModification', content: 'OK'});
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

    trackFacturaPresupuestoById(index: number, item: FacturaPresupuesto) {
        return item.id;
    }

    trackProductoById(index: number, item: Producto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-detalle-fact-pres-popup',
    template: ''
})
export class DetalleFactPresPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detalleFactPresPopupService: DetalleFactPresPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.detalleFactPresPopupService
                    .open(DetalleFactPresDialogComponent, params['id']);
            } else {
                this.modalRef = this.detalleFactPresPopupService
                    .open(DetalleFactPresDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
