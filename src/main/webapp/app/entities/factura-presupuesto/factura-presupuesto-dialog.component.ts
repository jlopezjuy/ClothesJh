import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoPopupService } from './factura-presupuesto-popup.service';
import { FacturaPresupuestoService } from './factura-presupuesto.service';
import { Cliente, ClienteService } from '../cliente';
import { ResponseWrapper } from '../../shared';
import { Producto } from '../producto/producto.model';
import { ProductoService } from '../producto/producto.service';
import { DetalleFactPres } from '../detalle-fact-pres/detalle-fact-pres.model';
import { DetalleFactPresService } from '../detalle-fact-pres/detalle-fact-pres.service';
ngToast

@Component({
    selector: 'jhi-factura-presupuesto-dialog',
    templateUrl: './factura-presupuesto-dialog.component.html'
})
export class FacturaPresupuestoDialogComponent implements OnInit {

    facturaPresupuesto: FacturaPresupuesto;
    detalleFactPres: DetalleFactPres;
    productoIdSelec: number;
    cantidadSelec: number;
    authorities: any[];
    isSaving: boolean;

    clientes: Cliente[];
    productos: Producto[];
    fechaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private facturaPresupuestoService: FacturaPresupuestoService,
        private clienteService: ClienteService,
        private productoService: ProductoService,
        private detalleFactPresService: DetalleFactPresService,
        private eventManager: JhiEventManager,
        private modalService: NgbModal,
        private router: Router,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clienteService.query().subscribe((res: ResponseWrapper) => { this.clientes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.productoService.query().subscribe((res: ResponseWrapper) => { this.productos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        console.log('Entro al metodo save');
        this.isSaving = true;
        if (this.facturaPresupuesto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facturaPresupuestoService.update(this.facturaPresupuesto), false);
        } else {
            this.subscribeToSaveResponse(
                this.facturaPresupuestoService.create(this.facturaPresupuesto), true);
        }
    }

    addToList() {
        console.log('entro al add to list');
        this.productoService.find(this.productoIdSelec).subscribe((producto) => {
            this.productoModalRef(producto);
        });
    }

    productoModalRef(producto: Producto) {
        console.log(producto);
        if (null !== producto || undefined !== producto) {
            producto.cantidadSeleccionada = this.cantidadSelec;
            producto.totalFila = producto.precioVenta * producto.cantidadSeleccionada;
            this.productos.push(producto);
        }
    }

    private subscribeToSaveResponse(result: Observable<FacturaPresupuesto>, isCreated: boolean) {
        result.subscribe((res: FacturaPresupuesto) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FacturaPresupuesto, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.facturaPresupuesto.created'
            : 'clothesApp.facturaPresupuesto.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'facturaPresupuestoListModification', content: 'OK'});
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

    trackProductoById(index: number, item: Producto) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-factura-presupuesto-popup',
    template: ''
})
export class FacturaPresupuestoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facturaPresupuestoPopupService: FacturaPresupuestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.facturaPresupuestoPopupService
                    .open(FacturaPresupuestoDialogComponent, params['id']);
            } else {
                this.modalRef = this.facturaPresupuestoPopupService
                    .open(FacturaPresupuestoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
