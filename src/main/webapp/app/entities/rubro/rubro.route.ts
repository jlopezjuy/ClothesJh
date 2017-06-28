import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RubroComponent } from './rubro.component';
import { RubroDetailComponent } from './rubro-detail.component';
import { RubroPopupComponent } from './rubro-dialog.component';
import { RubroDeletePopupComponent } from './rubro-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class RubroResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const rubroRoute: Routes = [
    {
        path: 'rubro',
        component: RubroComponent,
        resolve: {
            'pagingParams': RubroResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.rubro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rubro/:id',
        component: RubroDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.rubro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rubroPopupRoute: Routes = [
    {
        path: 'rubro-new',
        component: RubroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.rubro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rubro/:id/edit',
        component: RubroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.rubro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rubro/:id/delete',
        component: RubroDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.rubro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
