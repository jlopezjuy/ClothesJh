import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Encargo } from './encargo.model';
import { EncargoService } from './encargo.service';

@Injectable()
export class EncargoPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private encargoService: EncargoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

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
                this.encargoModalRef(component, encargo);
            });
        } else {
            return this.encargoModalRef(component, new Encargo());
        }
    }

    encargoModalRef(component: Component, encargo: Encargo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.encargo = encargo;
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
