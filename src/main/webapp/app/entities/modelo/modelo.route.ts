import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ModeloComponent } from './modelo.component';
import { ModeloDetailComponent } from './modelo-detail.component';
import { ModeloPopupComponent } from './modelo-dialog.component';
import { ModeloDeletePopupComponent } from './modelo-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ModeloResolvePagingParams implements Resolve<any> {

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

export const modeloRoute: Routes = [
    {
        path: 'modelo',
        component: ModeloComponent,
        resolve: {
            'pagingParams': ModeloResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modelo/:id',
        component: ModeloDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeloPopupRoute: Routes = [
    {
        path: 'modelo-new',
        component: ModeloPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo/:id/edit',
        component: ModeloPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modelo/:id/delete',
        component: ModeloDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clothesApp.modelo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
