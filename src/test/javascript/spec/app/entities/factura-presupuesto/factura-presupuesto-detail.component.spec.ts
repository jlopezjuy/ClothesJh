/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FacturaPresupuestoDetailComponent } from '../../../../../../main/webapp/app/entities/factura-presupuesto/factura-presupuesto-detail.component';
import { FacturaPresupuestoService } from '../../../../../../main/webapp/app/entities/factura-presupuesto/factura-presupuesto.service';
import { FacturaPresupuesto } from '../../../../../../main/webapp/app/entities/factura-presupuesto/factura-presupuesto.model';

describe('Component Tests', () => {

    describe('FacturaPresupuesto Management Detail Component', () => {
        let comp: FacturaPresupuestoDetailComponent;
        let fixture: ComponentFixture<FacturaPresupuestoDetailComponent>;
        let service: FacturaPresupuestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [FacturaPresupuestoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FacturaPresupuestoService,
                    JhiEventManager
                ]
            }).overrideTemplate(FacturaPresupuestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacturaPresupuestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacturaPresupuestoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FacturaPresupuesto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.facturaPresupuesto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
