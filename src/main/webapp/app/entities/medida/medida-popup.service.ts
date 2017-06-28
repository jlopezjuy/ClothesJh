import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Medida } from './medida.model';
import { MedidaService } from './medida.service';

@Injectable()
export class MedidaPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private medidaService: MedidaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.medidaService.find(id).subscribe((medida) => {
                if (medida.fechaMedida) {
                    medida.fechaMedida = {
                        year: medida.fechaMedida.getFullYear(),
                        month: medida.fechaMedida.getMonth() + 1,
                        day: medida.fechaMedida.getDate()
                    };
                }
                this.medidaModalRef(component, medida);
            });
        } else {
            return this.medidaModalRef(component, new Medida());
        }
    }

    medidaModalRef(component: Component, medida: Medida): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medida = medida;
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
