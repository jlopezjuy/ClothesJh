import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Pago } from './pago.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PagoService {

    private resourceUrl = 'api/pagos';
    private resourceUrlEncargo = 'api/pagos/encargo';
    private encargoId: number;
    private resourceSearchUrl = 'api/_search/pagos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pago: Pago): Observable<Pago> {
        pago.encargoId = this.encargoId;
        const copy = this.convert(pago);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(pago: Pago): Observable<Pago> {
        const copy = this.convert(pago);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Pago> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any, encargoId?: number): Observable<ResponseWrapper> {
        this.encargoId = encargoId;
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlEncargo}/${encargoId}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fechaPago = this.dateUtils
            .convertLocalDateFromServer(entity.fechaPago);
    }

    private convert(pago: Pago): Pago {
        const copy: Pago = Object.assign({}, pago);
        copy.fechaPago = this.dateUtils
            .convertLocalDateToServer(pago.fechaPago);
        return copy;
    }
}
