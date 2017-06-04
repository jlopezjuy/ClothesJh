import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Empresa } from './empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
    selector: 'jhi-empresa-detail',
    templateUrl: './empresa-detail.component.html'
})
export class EmpresaDetailComponent implements OnInit, OnDestroy {

    empresa: Empresa;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private empresaService: EmpresaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmpresas();
    }

    load(id) {
        this.empresaService.find(id).subscribe((empresa) => {
            this.empresa = empresa;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmpresas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'empresaListModification',
            (response) => this.load(this.empresa.id)
        );
    }
}
