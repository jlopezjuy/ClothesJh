import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Empresa } from './empresa.model';
import { EmpresaPopupService } from './empresa-popup.service';
import { EmpresaService } from './empresa.service';

@Component({
    selector: 'jhi-empresa-delete-dialog',
    templateUrl: './empresa-delete-dialog.component.html'
})
export class EmpresaDeleteDialogComponent {

    empresa: Empresa;

    constructor(
        private empresaService: EmpresaService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.empresaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'empresaListModification',
                content: 'Deleted an empresa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-empresa-delete-popup',
    template: ''
})
export class EmpresaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private empresaPopupService: EmpresaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.empresaPopupService
                .open(EmpresaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
