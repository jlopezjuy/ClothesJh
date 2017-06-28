import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Rubro } from './rubro.model';
import { RubroService } from './rubro.service';

@Component({
    selector: 'jhi-rubro-detail',
    templateUrl: './rubro-detail.component.html'
})
export class RubroDetailComponent implements OnInit, OnDestroy {

    rubro: Rubro;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rubroService: RubroService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRubros();
    }

    load(id) {
        this.rubroService.find(id).subscribe((rubro) => {
            this.rubro = rubro;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRubros() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rubroListModification',
            (response) => this.load(this.rubro.id)
        );
    }
}
