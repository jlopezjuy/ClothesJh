import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Proveedor } from './proveedor.model';
import { ProveedorPopupService } from './proveedor-popup.service';
import { ProveedorService } from './proveedor.service';

@Component({
    selector: 'jhi-proveedor-delete-dialog',
    templateUrl: './proveedor-delete-dialog.component.html'
})
export class ProveedorDeleteDialogComponent {

    proveedor: Proveedor;

    constructor(
        private proveedorService: ProveedorService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.proveedorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'proveedorListModification',
                content: 'Deleted an proveedor'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('clothesApp.proveedor.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-proveedor-delete-popup',
    template: ''
})
export class ProveedorDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private proveedorPopupService: ProveedorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.proveedorPopupService
                .open(ProveedorDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
