import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Proveedor } from './proveedor.model';
import { ProveedorService } from './proveedor.service';

@Component({
    selector: 'jhi-proveedor-detail',
    templateUrl: './proveedor-detail.component.html'
})
export class ProveedorDetailComponent implements OnInit, OnDestroy {

    proveedor: Proveedor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private proveedorService: ProveedorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProveedors();
    }

    load(id) {
        this.proveedorService.find(id).subscribe((proveedor) => {
            this.proveedor = proveedor;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProveedors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'proveedorListModification',
            (response) => this.load(this.proveedor.id)
        );
    }
}
