import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoService } from './factura-presupuesto.service';

@Component({
    selector: 'jhi-factura-presupuesto-detail',
    templateUrl: './factura-presupuesto-detail.component.html'
})
export class FacturaPresupuestoDetailComponent implements OnInit, OnDestroy {

    facturaPresupuesto: FacturaPresupuesto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private facturaPresupuestoService: FacturaPresupuestoService,
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
        });
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
