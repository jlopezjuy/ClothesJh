import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { FacturaPresupuesto } from './factura-presupuesto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FacturaPresupuestoService {

    private resourceUrl = 'api/factura-presupuestos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(facturaPresupuesto: FacturaPresupuesto): Observable<FacturaPresupuesto> {
        const copy = this.convert(facturaPresupuesto);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(facturaPresupuesto: FacturaPresupuesto): Observable<FacturaPresupuesto> {
        const copy = this.convert(facturaPresupuesto);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<FacturaPresupuesto> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fecha = this.dateUtils
            .convertLocalDateFromServer(entity.fecha);
    }

    private convert(facturaPresupuesto: FacturaPresupuesto): FacturaPresupuesto {
        const copy: FacturaPresupuesto = Object.assign({}, facturaPresupuesto);
        copy.fecha = this.dateUtils
            .convertLocalDateToServer(facturaPresupuesto.fecha);
        return copy;
    }
}
