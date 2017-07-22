import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoPopupService } from './factura-presupuesto-popup.service';
import { FacturaPresupuestoService } from './factura-presupuesto.service';

@Component({
    selector: 'jhi-factura-presupuesto-delete-dialog',
    templateUrl: './factura-presupuesto-delete-dialog.component.html'
})
export class FacturaPresupuestoDeleteDialogComponent {

    facturaPresupuesto: FacturaPresupuesto;

    constructor(
        private facturaPresupuestoService: FacturaPresupuestoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facturaPresupuestoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facturaPresupuestoListModification',
                content: 'Deleted an facturaPresupuesto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-factura-presupuesto-delete-popup',
    template: ''
})
export class FacturaPresupuestoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facturaPresupuestoPopupService: FacturaPresupuestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.facturaPresupuestoPopupService
                .open(FacturaPresupuestoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
