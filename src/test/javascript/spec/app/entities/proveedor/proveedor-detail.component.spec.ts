import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ClothesTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProveedorDetailComponent } from '../../../../../../main/webapp/app/entities/proveedor/proveedor-detail.component';
import { ProveedorService } from '../../../../../../main/webapp/app/entities/proveedor/proveedor.service';
import { Proveedor } from '../../../../../../main/webapp/app/entities/proveedor/proveedor.model';

describe('Component Tests', () => {

    describe('Proveedor Management Detail Component', () => {
        let comp: ProveedorDetailComponent;
        let fixture: ComponentFixture<ProveedorDetailComponent>;
        let service: ProveedorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClothesTestModule],
                declarations: [ProveedorDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProveedorService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProveedorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProveedorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProveedorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Proveedor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.proveedor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
