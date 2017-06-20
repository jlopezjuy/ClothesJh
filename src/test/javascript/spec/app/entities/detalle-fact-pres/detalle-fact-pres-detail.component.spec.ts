import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DetalleFactPresDetailComponent } from '../../../../../../main/webapp/app/entities/detalle-fact-pres/detalle-fact-pres-detail.component';
import { DetalleFactPresService } from '../../../../../../main/webapp/app/entities/detalle-fact-pres/detalle-fact-pres.service';
import { DetalleFactPres } from '../../../../../../main/webapp/app/entities/detalle-fact-pres/detalle-fact-pres.model';

describe('Component Tests', () => {

    describe('DetalleFactPres Management Detail Component', () => {
        let comp: DetalleFactPresDetailComponent;
        let fixture: ComponentFixture<DetalleFactPresDetailComponent>;
        let service: DetalleFactPresService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [DetalleFactPresDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DetalleFactPresService,
                    JhiEventManager
                ]
            }).overrideTemplate(DetalleFactPresDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetalleFactPresDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetalleFactPresService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DetalleFactPres(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.detalleFactPres).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
