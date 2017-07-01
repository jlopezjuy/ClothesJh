import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoService } from './factura-presupuesto.service';

import {Producto} from '../producto/producto.model';
import {ProductoService} from '../producto/producto.service';
import {DetalleFactPres} from '../detalle-fact-pres/detalle-fact-pres.model';
import {DetalleFactPresService} from '../detalle-fact-pres/detalle-fact-pres.service';
import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { ResponseWrapper } from '../../shared/model/response-wrapper.model';

@Component({
    selector: 'jhi-factura-presupuesto-detail',
    templateUrl: './factura-presupuesto-detail.component.html'
})
export class FacturaPresupuestoDetailComponent implements OnInit, OnDestroy {

    facturaPresupuesto: FacturaPresupuesto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    predicate: any;
    previousPage: any;
    reverse: any;
    productos: Producto[];
    productosList: Producto[] = [];
    listaVendidos: DetalleFactPres[] = [];

    constructor(
        private eventManager: JhiEventManager,
        private facturaPresupuestoService: FacturaPresupuestoService,
        private productoService: ProductoService,
        private detalleFactPresService: DetalleFactPresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacturaPresupuestos();
    }

    load(id) {
        this.facturaPresupuestoService.find(id).subscribe((facturaPresupuesto) => {
            this.facturaPresupuesto = facturaPresupuesto;
            this.detalleFactPresService.queryFactura(this.facturaPresupuesto.id).subscribe((facturas) => {
                console.log(facturas);
                this.listaVendidos = facturas.json;
                this.listaVendidos.forEach((venta) => {
                    this.productoService.find(venta.productoId).subscribe((producto) => {
                        console.log(producto);
                        producto.cantidadSeleccionada = venta.cantidad;
                        producto.totalFila = venta.predio;
                        this.productosList.push(producto);
                    });
                });
            });
        });

    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacturaPresupuestos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facturaPresupuestoListModification',
            (response) => this.load(this.facturaPresupuesto.id)
        );
    }
}
