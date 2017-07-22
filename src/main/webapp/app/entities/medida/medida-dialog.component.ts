import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Medida } from './medida.model';
import { MedidaPopupService } from './medida-popup.service';
import { MedidaService } from './medida.service';
import { Encargo, EncargoService } from '../encargo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-medida-dialog',
    templateUrl: './medida-dialog.component.html'
})
export class MedidaDialogComponent implements OnInit {

    medida: Medida;
    isSaving: boolean;

    encargos: Encargo[];
    fechaMedidaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private medidaService: MedidaService,
        private encargoService: EncargoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.encargoService.query()
            .subscribe((res: ResponseWrapper) => { this.encargos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.medida.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medidaService.update(this.medida));
        } else {
            this.subscribeToSaveResponse(
                this.medidaService.create(this.medida));
        }
    }

    private subscribeToSaveResponse(result: Observable<Medida>) {
        result.subscribe((res: Medida) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Medida) {
        this.eventManager.broadcast({ name: 'medidaListModification', content: 'OK'});
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

    trackEncargoById(index: number, item: Encargo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-medida-popup',
    template: ''
})
export class MedidaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medidaPopupService: MedidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medidaPopupService
                    .open(MedidaDialogComponent as Component, params['id']);
            } else {
                this.medidaPopupService
                    .open(MedidaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
