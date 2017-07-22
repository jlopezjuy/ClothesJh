import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Encargo } from './encargo.model';
import { EncargoService } from './encargo.service';

@Injectable()
export class EncargoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private encargoService: EncargoService

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
                this.encargoService.find(id).subscribe((encargo) => {
                    if (encargo.fechaEncargo) {
                        encargo.fechaEncargo = {
                            year: encargo.fechaEncargo.getFullYear(),
                            month: encargo.fechaEncargo.getMonth() + 1,
                            day: encargo.fechaEncargo.getDate()
                        };
                    }
                    if (encargo.fechaEntrega) {
                        encargo.fechaEntrega = {
                            year: encargo.fechaEntrega.getFullYear(),
                            month: encargo.fechaEntrega.getMonth() + 1,
                            day: encargo.fechaEntrega.getDate()
                        };
                    }
                    this.ngbModalRef = this.encargoModalRef(component, encargo);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.encargoModalRef(component, new Encargo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    encargoModalRef(component: Component, encargo: Encargo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.encargo = encargo;
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
