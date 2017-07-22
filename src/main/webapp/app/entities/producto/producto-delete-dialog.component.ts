import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Producto } from './producto.model';
import { ProductoPopupService } from './producto-popup.service';
import { ProductoService } from './producto.service';

@Component({
    selector: 'jhi-producto-delete-dialog',
    templateUrl: './producto-delete-dialog.component.html'
})
export class ProductoDeleteDialogComponent {

    producto: Producto;

    constructor(
        private productoService: ProductoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productoListModification',
                content: 'Deleted an producto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-producto-delete-popup',
    template: ''
})
export class ProductoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productoPopupService: ProductoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productoPopupService
                .open(ProductoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
