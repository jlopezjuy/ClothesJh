import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rubro } from './rubro.model';
import { RubroPopupService } from './rubro-popup.service';
import { RubroService } from './rubro.service';

@Component({
    selector: 'jhi-rubro-dialog',
    templateUrl: './rubro-dialog.component.html'
})
export class RubroDialogComponent implements OnInit {

    rubro: Rubro;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private rubroService: RubroService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rubro.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rubroService.update(this.rubro));
        } else {
            this.subscribeToSaveResponse(
                this.rubroService.create(this.rubro));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rubro>) {
        result.subscribe((res: Rubro) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Rubro) {
        this.eventManager.broadcast({ name: 'rubroListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-rubro-popup',
    template: ''
})
export class RubroPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rubroPopupService: RubroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rubroPopupService
                    .open(RubroDialogComponent as Component, params['id']);
            } else {
                this.rubroPopupService
                    .open(RubroDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
