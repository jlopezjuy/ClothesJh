import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Empresa } from './empresa.model';
import { EmpresaPopupService } from './empresa-popup.service';
import { EmpresaService } from './empresa.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-empresa-dialog',
    templateUrl: './empresa-dialog.component.html'
})
export class EmpresaDialogComponent implements OnInit {

    empresa: Empresa;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private empresaService: EmpresaService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.empresa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.empresaService.update(this.empresa));
        } else {
            this.subscribeToSaveResponse(
                this.empresaService.create(this.empresa));
        }
    }

    private subscribeToSaveResponse(result: Observable<Empresa>) {
        result.subscribe((res: Empresa) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Empresa) {
        this.eventManager.broadcast({ name: 'empresaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-empresa-popup',
    template: ''
})
export class EmpresaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private empresaPopupService: EmpresaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.empresaPopupService
                    .open(EmpresaDialogComponent, params['id']);
            } else {
                this.modalRef = this.empresaPopupService
                    .open(EmpresaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
