import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Producto } from './producto.model';
import { ProductoService } from './producto.service';

@Component({
    selector: 'jhi-producto-detail',
    templateUrl: './producto-detail.component.html'
})
export class ProductoDetailComponent implements OnInit, OnDestroy {

    producto: Producto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private productoService: ProductoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductos();
    }

    load(id) {
        this.productoService.find(id).subscribe((producto) => {
            this.producto = producto;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productoListModification',
            (response) => this.load(this.producto.id)
        );
    }
}
