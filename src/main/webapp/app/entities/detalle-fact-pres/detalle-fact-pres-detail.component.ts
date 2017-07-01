import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { DetalleFactPres } from './detalle-fact-pres.model';
import { DetalleFactPresService } from './detalle-fact-pres.service';

@Component({
    selector: 'jhi-detalle-fact-pres-detail',
    templateUrl: './detalle-fact-pres-detail.component.html'
})
export class DetalleFactPresDetailComponent implements OnInit, OnDestroy {

    detalleFactPres: DetalleFactPres;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detalleFactPresService: DetalleFactPresService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetalleFactPres();
    }

    load(id) {
        this.detalleFactPresService.find(id).subscribe((detalleFactPres) => {
            this.detalleFactPres = detalleFactPres;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetalleFactPres() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detalleFactPresListModification',
            (response) => this.load(this.detalleFactPres.id)
        );
    }
}
