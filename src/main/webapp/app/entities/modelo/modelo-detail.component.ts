import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Modelo } from './modelo.model';
import { ModeloService } from './modelo.service';

@Component({
    selector: 'jhi-modelo-detail',
    templateUrl: './modelo-detail.component.html'
})
export class ModeloDetailComponent implements OnInit, OnDestroy {

    modelo: Modelo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private modeloService: ModeloService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInModelos();
    }

    load(id) {
        this.modeloService.find(id).subscribe((modelo) => {
            this.modelo = modelo;
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

    registerChangeInModelos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'modeloListModification',
            (response) => this.load(this.modelo.id)
        );
    }
}
