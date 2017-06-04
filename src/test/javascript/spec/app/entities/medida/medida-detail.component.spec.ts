import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MedidaDetailComponent } from '../../../../../../main/webapp/app/entities/medida/medida-detail.component';
import { MedidaService } from '../../../../../../main/webapp/app/entities/medida/medida.service';
import { Medida } from '../../../../../../main/webapp/app/entities/medida/medida.model';

describe('Component Tests', () => {

    describe('Medida Management Detail Component', () => {
        let comp: MedidaDetailComponent;
        let fixture: ComponentFixture<MedidaDetailComponent>;
        let service: MedidaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [MedidaDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MedidaService,
                    EventManager
                ]
            }).overrideComponent(MedidaDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MedidaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MedidaService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Medida(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.medida).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
