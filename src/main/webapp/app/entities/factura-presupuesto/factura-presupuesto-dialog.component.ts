import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {FacturaPresupuesto} from './factura-presupuesto.model';
import {FacturaPresupuestoPopupService} from './factura-presupuesto-popup.service';
import {FacturaPresupuestoService} from './factura-presupuesto.service';
import {Cliente, ClienteService} from '../cliente';
import {ResponseWrapper} from '../../shared';
import {Producto} from '../producto/producto.model';
import {ProductoService} from '../producto/producto.service';
import {DetalleFactPres} from '../detalle-fact-pres/detalle-fact-pres.model';
import {DetalleFactPresService} from '../detalle-fact-pres/detalle-fact-pres.service';

@Component({
    selector: 'jhi-factura-presupuesto-dialog',
    templateUrl: './factura-presupuesto-dialog.component.html'
})
export class FacturaPresupuestoDialogComponent implements OnInit {

    facturaPresupuesto: FacturaPresupuesto;
    venta: DetalleFactPres;
    productoIdSelec: number;
    cantidadSelec: number;
    authorities: any[];
    isSaving: boolean;
    fechaHoy: any;

    clientes: Cliente[];
    productos: Producto[];
    productosList: Producto[] = [];
    listaVendidos: DetalleFactPres[] = [];
    fechaDp: any;

    constructor(public activeModal: NgbActiveModal,
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
        this.clienteService.query().subscribe((res: ResponseWrapper) => {
            this.clientes = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.productoService.query().subscribe((res: ResponseWrapper) => {
            this.productos = res.json;
        }, (res: ResponseWrapper) => this.onError(res.json));
        this.facturaPresupuesto = new FacturaPresupuesto(null, new Date(), null, null, null, null);
        this.fechaHoy = new Date();
        this.fechaDp = new Date();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
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
        if (undefined !== this.cantidadSelec || null !== this.cantidadSelec) {
            this.productoService.find(this.productoIdSelec).subscribe((producto) => {
                this.productoModalRef(producto);
            });
        } else {
            this.alertService.error('clothesApp.facturaPresupuesto.errorProducto', null, null);
        }
    }

    productoModalRef(producto: Producto) {
        if (null !== producto || undefined !== producto) {
            console.log('Cantidad seleccionada: ', this.cantidadSelec);
            if (undefined === this.cantidadSelec || null === this.cantidadSelec) {
                this.alertService.error('clothesApp.facturaPresupuesto.errorCantidad', {param: producto.nombre}, null);
            } else {
                producto.cantidadSeleccionada = this.cantidadSelec;
                producto.totalFila = producto.precioVenta * producto.cantidadSeleccionada;
                this.productosList.push(producto);
                if (undefined === this.facturaPresupuesto.importeTotal || null === this.facturaPresupuesto.importeTotal) {
                    this.facturaPresupuesto.importeTotal = 0;
                }
                this.facturaPresupuesto.importeTotal = this.facturaPresupuesto.importeTotal + producto.totalFila;
                this.productoIdSelec = null;
                this.cantidadSelec = null;
            }
        }
    }

    private addVenta(idFactura?: number) {
        this.productosList.forEach((venta) => {
            const detalle = new DetalleFactPres();
            detalle.facturaPresupuestoId = idFactura;
            detalle.cantidad = venta.cantidadSeleccionada;
            detalle.predio = venta.totalFila;
            detalle.productoId = venta.id;
            this.listaVendidos.push(detalle);
        });
    }

    private subscribeToSaveResponse(result: Observable<FacturaPresupuesto>, isCreated: boolean) {
        result.subscribe((res: FacturaPresupuesto) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FacturaPresupuesto, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.facturaPresupuesto.created'
                : 'clothesApp.facturaPresupuesto.updated',
            {param: result.id}, null);

        this.eventManager.broadcast({name: 'facturaPresupuestoListModification', content: 'OK'});
        this.isSaving = false;
        this.addVenta(result.id);
        this.listaVendidos.forEach((salida) => {
            console.log(salida);
            this.saveDetalleVenta(salida);
        });
        this.activeModal.dismiss(result);
    }

    saveDetalleVenta(venta: DetalleFactPres) {
        if (venta.id !== undefined) {
            this.subscribeToSaveResponseVenta(
                this.detalleFactPresService.update(venta), true);
        } else {
            this.subscribeToSaveResponseVenta(
                this.detalleFactPresService.create(venta), true);
        }
    }

    private subscribeToSaveResponseVenta(result: Observable<DetalleFactPres>, isCreated: boolean) {
        result.subscribe((res: DetalleFactPres) =>
            this.onSaveSuccessVenta(res, isCreated), (res: Response) => this.onSaveErrorVenta(res));
    }

    private onSaveSuccessVenta(result: DetalleFactPres, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.facturaPresupuesto.created'
                : 'clothesApp.facturaPresupuesto.updated',
            {param: result.id}, null);
        console.log('Ok');
    }

    private onSaveErrorVenta(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
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

    constructor(private route: ActivatedRoute,
                private facturaPresupuestoPopupService: FacturaPresupuestoPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
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
