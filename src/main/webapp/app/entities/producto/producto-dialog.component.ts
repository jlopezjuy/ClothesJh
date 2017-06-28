import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Producto } from './producto.model';
import { ProductoPopupService } from './producto-popup.service';
import { ProductoService } from './producto.service';
import { Proveedor, ProveedorService } from '../proveedor';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-producto-dialog',
    templateUrl: './producto-dialog.component.html'
})
export class ProductoDialogComponent implements OnInit {

    producto: Producto;
    authorities: any[];
    isSaving: boolean;

    proveedors: Proveedor[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private productoService: ProductoService,
        private proveedorService: ProveedorService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.proveedorService.query()
            .subscribe((res: ResponseWrapper) => { this.proveedors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, producto, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                producto[field] = base64Data;
                producto[`${field}ContentType`] = file.type;
            });
        }
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.producto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productoService.update(this.producto), false);
        } else {
            this.calculoFinal();
            this.subscribeToSaveResponse(
                this.productoService.create(this.producto), true);
        }
    }

    private calculoFinal() {
        this.producto.margenGanancia = this.producto.cantidad * (this.producto.precioVenta - this.producto.precioCompra);
    }

    private subscribeToSaveResponse(result: Observable<Producto>, isCreated: boolean) {
        result.subscribe((res: Producto) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Producto, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.producto.created'
            : 'clothesApp.producto.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'productoListModification', content: 'OK'});
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

    trackProveedorById(index: number, item: Proveedor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-producto-popup',
    template: ''
})
export class ProductoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productoPopupService: ProductoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.productoPopupService
                    .open(ProductoDialogComponent, params['id']);
            } else {
                this.modalRef = this.productoPopupService
                    .open(ProductoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
