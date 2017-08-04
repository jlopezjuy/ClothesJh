import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Medida } from './medida.model';
import { MedidaService } from './medida.service';

@Injectable()
export class MedidaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private medidaService: MedidaService

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
                this.medidaService.find(id).subscribe((medida) => {
                    if (medida.fechaMedida) {
                        medida.fechaMedida = {
                            year: medida.fechaMedida.getFullYear(),
                            month: medida.fechaMedida.getMonth() + 1,
                            day: medida.fechaMedida.getDate()
                        };
                    }
                    this.ngbModalRef = this.medidaModalRef(component, medida);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.medidaModalRef(component, new Medida());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    medidaModalRef(component: Component, medida: Medida): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.medida = medida;
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
