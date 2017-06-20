import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Proveedor } from './proveedor.model';
import { ProveedorService } from './proveedor.service';

@Injectable()
export class ProveedorPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private proveedorService: ProveedorService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.proveedorService.find(id).subscribe((proveedor) => {
                this.proveedorModalRef(component, proveedor);
            });
        } else {
            return this.proveedorModalRef(component, new Proveedor());
        }
    }

    proveedorModalRef(component: Component, proveedor: Proveedor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.proveedor = proveedor;
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
