import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Pago } from './pago.model';
import { PagoService } from './pago.service';

@Component({
    selector: 'jhi-pago-detail',
    templateUrl: './pago-detail.component.html'
})
export class PagoDetailComponent implements OnInit, OnDestroy {

    pago: Pago;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pagoService: PagoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPagos();
    }

    load(id) {
        this.pagoService.find(id).subscribe((pago) => {
            this.pago = pago;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPagos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pagoListModification',
            (response) => this.load(this.pago.id)
        );
    }
}
