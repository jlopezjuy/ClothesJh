import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Rubro } from './rubro.model';
import { RubroPopupService } from './rubro-popup.service';
import { RubroService } from './rubro.service';

@Component({
    selector: 'jhi-rubro-delete-dialog',
    templateUrl: './rubro-delete-dialog.component.html'
})
export class RubroDeleteDialogComponent {

    rubro: Rubro;

    constructor(
        private rubroService: RubroService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rubroService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rubroListModification',
                content: 'Deleted an rubro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rubro-delete-popup',
    template: ''
})
export class RubroDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rubroPopupService: RubroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.rubroPopupService
                .open(RubroDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
