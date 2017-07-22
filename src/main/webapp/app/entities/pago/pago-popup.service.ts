import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pago } from './pago.model';
import { PagoService } from './pago.service';

@Injectable()
export class PagoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pagoService: PagoService

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
                this.pagoService.find(id).subscribe((pago) => {
                    if (pago.fechaPago) {
                        pago.fechaPago = {
                            year: pago.fechaPago.getFullYear(),
                            month: pago.fechaPago.getMonth() + 1,
                            day: pago.fechaPago.getDate()
                        };
                    }
                    this.ngbModalRef = this.pagoModalRef(component, pago);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pagoModalRef(component, new Pago());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pagoModalRef(component: Component, pago: Pago): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pago = pago;
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
