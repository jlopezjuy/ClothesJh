import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Modelo } from './modelo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ModeloService {

    private resourceUrl = 'api/modelos';
    private resourceUrlCliente = 'api/modelosCliente';
    private clienteId:number;

    constructor(private http: Http) { }

    create(modelo: Modelo): Observable<Modelo> {
        console.log("cliente id en servicio: " + this.clienteId);
        modelo.clienteId = this.clienteId;
        const copy = this.convert(modelo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(modelo: Modelo): Observable<Modelo> {
        console.log("modelo editado: " + modelo);
        console.log(modelo);

        const copy = this.convert(modelo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Modelo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any, idCliente?:number): Observable<ResponseWrapper> {
        this.clienteId = idCliente;
        console.log("guardado: "+ this.clienteId);
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlCliente}/${idCliente}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(modelo: Modelo): Modelo {
        const copy: Modelo = Object.assign({}, modelo);
        return copy;
    }
}
