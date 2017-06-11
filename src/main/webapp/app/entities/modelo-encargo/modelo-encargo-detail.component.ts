import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , DataUtils } from 'ng-jhipster';

import { ModeloEncargo } from './modelo-encargo.model';
import { ModeloEncargoService } from './modelo-encargo.service';

@Component({
    selector: 'jhi-modelo-encargo-detail',
    templateUrl: './modelo-encargo-detail.component.html'
})
export class ModeloEncargoDetailComponent implements OnInit, OnDestroy {

    modeloEncargo: ModeloEncargo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private dataUtils: DataUtils,
        private modeloEncargoService: ModeloEncargoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInModeloEncargos();
    }

    load(id) {
        this.modeloEncargoService.find(id).subscribe((modeloEncargo) => {
            this.modeloEncargo = modeloEncargo;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInModeloEncargos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'modeloEncargoListModification',
            (response) => this.load(this.modeloEncargo.id)
        );
    }
}
