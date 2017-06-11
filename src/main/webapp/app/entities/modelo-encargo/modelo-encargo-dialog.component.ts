import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { ModeloEncargo } from './modelo-encargo.model';
import { ModeloEncargoPopupService } from './modelo-encargo-popup.service';
import { ModeloEncargoService } from './modelo-encargo.service';
import { Encargo, EncargoService } from '../encargo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-modelo-encargo-dialog',
    templateUrl: './modelo-encargo-dialog.component.html'
})
export class ModeloEncargoDialogComponent implements OnInit {

    modeloEncargo: ModeloEncargo;
    authorities: any[];
    isSaving: boolean;

    encargos: Encargo[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private modeloEncargoService: ModeloEncargoService,
        private encargoService: EncargoService,
        private elementRef: ElementRef,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.encargoService.query()
            .subscribe((res: ResponseWrapper) => { this.encargos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, modeloEncargo, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                modeloEncargo[field] = base64Data;
                modeloEncargo[`${field}ContentType`] = file.type;
            });
        }
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.modeloEncargo, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.modeloEncargo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeloEncargoService.update(this.modeloEncargo), false);
        } else {
            this.subscribeToSaveResponse(
                this.modeloEncargoService.create(this.modeloEncargo), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ModeloEncargo>, isCreated: boolean) {
        result.subscribe((res: ModeloEncargo) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ModeloEncargo, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'clothesApp.modeloEncargo.created'
            : 'clothesApp.modeloEncargo.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'modeloEncargoListModification', content: 'OK'});
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
    selector: 'jhi-modelo-encargo-popup',
    template: ''
})
export class ModeloEncargoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeloEncargoPopupService: ModeloEncargoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.modeloEncargoPopupService
                    .open(ModeloEncargoDialogComponent, params['id']);
            } else {
                this.modalRef = this.modeloEncargoPopupService
                    .open(ModeloEncargoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
