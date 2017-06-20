import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Pago } from './pago.model';
import { PagoService } from './pago.service';

@Injectable()
export class PagoPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pagoService: PagoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pagoService.find(id).subscribe((pago) => {
                if (pago.fechaPago) {
                    pago.fechaPago = {
                        year: pago.fechaPago.getFullYear(),
                        month: pago.fechaPago.getMonth() + 1,
                        day: pago.fechaPago.getDate()
                    };
                }
                this.pagoModalRef(component, pago);
            });
        } else {
            return this.pagoModalRef(component, new Pago());
        }
    }

    pagoModalRef(component: Component, pago: Pago): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pago = pago;
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
