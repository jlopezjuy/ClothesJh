import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pago } from './pago.model';
import { PagoPopupService } from './pago-popup.service';
import { PagoService } from './pago.service';
import { Encargo, EncargoService } from '../encargo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pago-dialog',
    templateUrl: './pago-dialog.component.html'
})
export class PagoDialogComponent implements OnInit {

    pago: Pago;
    isSaving: boolean;

    encargos: Encargo[];
    fechaPagoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pagoService: PagoService,
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
        if (this.pago.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pagoService.update(this.pago));
        } else {
            this.subscribeToSaveResponse(
                this.pagoService.create(this.pago));
        }
    }

    private subscribeToSaveResponse(result: Observable<Pago>) {
        result.subscribe((res: Pago) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Pago) {
        this.eventManager.broadcast({ name: 'pagoListModification', content: 'OK'});
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
    selector: 'jhi-pago-popup',
    template: ''
})
export class PagoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pagoPopupService: PagoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pagoPopupService
                    .open(PagoDialogComponent as Component, params['id']);
            } else {
                this.pagoPopupService
                    .open(PagoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
