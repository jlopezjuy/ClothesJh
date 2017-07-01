import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoService } from './factura-presupuesto.service';

@Injectable()
export class FacturaPresupuestoPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facturaPresupuestoService: FacturaPresupuestoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facturaPresupuestoService.find(id).subscribe((facturaPresupuesto) => {
                if (facturaPresupuesto.fecha) {
                    facturaPresupuesto.fecha = {
                        year: facturaPresupuesto.fecha.getFullYear(),
                        month: facturaPresupuesto.fecha.getMonth() + 1,
                        day: facturaPresupuesto.fecha.getDate()
                    };
                }
                this.facturaPresupuestoModalRef(component, facturaPresupuesto);
            });
        } else {
            return this.facturaPresupuestoModalRef(component, new FacturaPresupuesto());
        }
    }

    facturaPresupuestoModalRef(component: Component, facturaPresupuesto: FacturaPresupuesto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facturaPresupuesto = facturaPresupuesto;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
