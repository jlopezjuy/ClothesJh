import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacturaPresupuesto } from './factura-presupuesto.model';
import { FacturaPresupuestoService } from './factura-presupuesto.service';

@Injectable()
export class FacturaPresupuestoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facturaPresupuestoService: FacturaPresupuestoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.facturaPresupuestoService.find(id).subscribe((facturaPresupuesto) => {
                    if (facturaPresupuesto.fecha) {
                        facturaPresupuesto.fecha = {
                            year: facturaPresupuesto.fecha.getFullYear(),
                            month: facturaPresupuesto.fecha.getMonth() + 1,
                            day: facturaPresupuesto.fecha.getDate()
                        };
                    }
                    this.ngbModalRef = this.facturaPresupuestoModalRef(component, facturaPresupuesto);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.facturaPresupuestoModalRef(component, new FacturaPresupuesto());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    facturaPresupuestoModalRef(component: Component, facturaPresupuesto: FacturaPresupuesto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facturaPresupuesto = facturaPresupuesto;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
