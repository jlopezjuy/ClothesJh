import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DetalleFactPres } from './detalle-fact-pres.model';
import { DetalleFactPresService } from './detalle-fact-pres.service';

@Injectable()
export class DetalleFactPresPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private detalleFactPresService: DetalleFactPresService

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
                this.detalleFactPresService.find(id).subscribe((detalleFactPres) => {
                    this.ngbModalRef = this.detalleFactPresModalRef(component, detalleFactPres);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.detalleFactPresModalRef(component, new DetalleFactPres());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    detalleFactPresModalRef(component: Component, detalleFactPres: DetalleFactPres): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.detalleFactPres = detalleFactPres;
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
