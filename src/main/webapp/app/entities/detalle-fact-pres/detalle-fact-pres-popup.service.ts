import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DetalleFactPres } from './detalle-fact-pres.model';
import { DetalleFactPresService } from './detalle-fact-pres.service';

@Injectable()
export class DetalleFactPresPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detalleFactPresService: DetalleFactPresService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.detalleFactPresService.find(id).subscribe((detalleFactPres) => {
                this.detalleFactPresModalRef(component, detalleFactPres);
            });
        } else {
            return this.detalleFactPresModalRef(component, new DetalleFactPres());
        }
    }

    detalleFactPresModalRef(component: Component, detalleFactPres: DetalleFactPres): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detalleFactPres = detalleFactPres;
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
