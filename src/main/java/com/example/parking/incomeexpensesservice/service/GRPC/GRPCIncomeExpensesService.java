package com.example.parking.incomeexpensesservice.service.GRPC;

import com.example.parking.incomeexpensesservice.model.Accountant;
import com.example.parking.incomeexpensesservice.model.CW_Type;
import com.example.parking.incomeexpensesservice.model.CommunalWorker;
import com.example.parking.incomeexpensesservice.model.Malfunction;
import com.example.parking.incomeexpensesservice.repositories.AccountantRepository;
import com.example.parking.incomeexpensesservice.repositories.CommunalWorkerRepository;
import com.example.parking.incomeexpensesservice.repositories.MalfunctionRepository;
import com.incomeExpensesService.grpc.IncomeExpensesServiceGrpc;
import com.incomeExpensesService.grpc.IncomeExpensesServiceOuterClass;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@GRpcService
public class GRPCIncomeExpensesService extends IncomeExpensesServiceGrpc.IncomeExpensesServiceImplBase {

    @Autowired
    MalfunctionRepository malfunctionRepository;
    @Autowired
    CommunalWorkerRepository communalWorkerRepository;
    @Autowired
    AccountantRepository accountantRepository;

    @Override
    public void grpcGetMalfunction(IncomeExpensesServiceOuterClass.UUID request, StreamObserver<IncomeExpensesServiceOuterClass.Malfunction> responseObserver) {
        String id = request.getValue();
        UUID uuid = UUID.fromString(id);
        Optional<Malfunction> tempMalf = malfunctionRepository.findById(uuid);
        Malfunction malfunction;
        IncomeExpensesServiceOuterClass.Malfunction.Builder response =
                IncomeExpensesServiceOuterClass.Malfunction.newBuilder();

        if(tempMalf.isPresent())
        {
            malfunction = tempMalf.get();
            response.setId(malfunction.getMalfunctionId().toString());
            response.setFixPrice(malfunction.getFixPrice());
            response.setIsPayed(malfunction.isPayed());
            response.setMalfunctionDescription(malfunction.getMalfunctionDesc());
            response.setWorkerId(malfunction.getWorkerId().toString());
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAllMalfunctions(IncomeExpensesServiceOuterClass.Empty request, StreamObserver<IncomeExpensesServiceOuterClass.Malfunctions> responseObserver) {
        List<Malfunction>  malfunctions= malfunctionRepository.findAll();
        IncomeExpensesServiceOuterClass.Malfunctions.Builder responseMalfunctions  =
                IncomeExpensesServiceOuterClass.Malfunctions.newBuilder();
        for(Malfunction m : malfunctions)
        {
            IncomeExpensesServiceOuterClass.Malfunction.Builder malfToList = IncomeExpensesServiceOuterClass.Malfunction.newBuilder();
            malfToList.setId(m.getMalfunctionId().toString());
            malfToList.setFixPrice(m.getFixPrice());
            malfToList.setIsPayed(m.isPayed());
            malfToList.setMalfunctionDescription(m.getMalfunctionDesc());
            malfToList.setWorkerId(m.getWorkerId().toString());
            responseMalfunctions.addMalfunctions(malfToList);
        }
        responseObserver.onNext(responseMalfunctions.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcAddMalfunctionToWorker(IncomeExpensesServiceOuterClass.addMalfunctionToWorker request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        String workerId = request.getWorkerId();
        IncomeExpensesServiceOuterClass.Malfunction malfunction =
                request.getMalfunction();
        Malfunction JavaMalfunction = new Malfunction(UUID.fromString(malfunction.getId()),
                malfunction.getFixPrice(),
                malfunction.getMalfunctionDescription(),
                malfunction.getIsPayed(),
                UUID.fromString(workerId));
        malfunctionRepository.save(JavaMalfunction);
        IncomeExpensesServiceOuterClass.Empty.Builder response =
                IncomeExpensesServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcGetWorker(IncomeExpensesServiceOuterClass.UUID request, StreamObserver<IncomeExpensesServiceOuterClass.CommunalWorker> responseObserver) {
       String id = request.getValue();
        UUID uuid = UUID.fromString(id);
        Optional<CommunalWorker> tempCW = communalWorkerRepository.findById(uuid);
        CommunalWorker communalWorker;
        IncomeExpensesServiceOuterClass.CommunalWorker.Builder response  =
                IncomeExpensesServiceOuterClass.CommunalWorker.newBuilder();
        if(tempCW.isPresent())
        {
            communalWorker = tempCW.get();
            response.setId(communalWorker.getCwId().toString());
            response.setName(communalWorker.getName());
            response.setSalary(communalWorker.getSalary());
            CW_Type cw_type = communalWorker.getCwType();
            response.setIsWinter(communalWorker.isWinter());
            if(cw_type==CW_Type.GAS) {
                response.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.GAS);
            }
            else if (cw_type==CW_Type.WATER)
            {
                response.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.WATER);
            }
            else
            {
                response.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.ELECTRICITY);
            }


        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAllWorkers(IncomeExpensesServiceOuterClass.Empty request, StreamObserver<IncomeExpensesServiceOuterClass.CommunalWorkers> responseObserver) {
        List<CommunalWorker> communalWorkers = communalWorkerRepository.findAll();
        IncomeExpensesServiceOuterClass.CommunalWorkers.Builder workersResponse=
                IncomeExpensesServiceOuterClass.CommunalWorkers.newBuilder();
        for (CommunalWorker cw : communalWorkers)
        {
            IncomeExpensesServiceOuterClass.CommunalWorker.Builder cwtToList  =
                    IncomeExpensesServiceOuterClass.CommunalWorker.newBuilder();
            cwtToList.setId(cw.getCwId().toString());
            cwtToList.setName(cw.getName());
            cwtToList.setSalary(cw.getSalary());
            CW_Type cw_type = cw.getCwType();
            cwtToList.setIsWinter(cw.isWinter());
            if(cw_type==CW_Type.GAS) {
                cwtToList.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.GAS);
            }
            else if (cw_type==CW_Type.WATER)
            {
                cwtToList.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.WATER);
            }
            else
            {
                cwtToList.setCwtype(IncomeExpensesServiceOuterClass.
                        CommunalWorker.CWType.ELECTRICITY);
            }
            workersResponse.addWorkers(cwtToList);

        }
        responseObserver.onNext(workersResponse.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcUpdateWorker(IncomeExpensesServiceOuterClass.CommunalWorker request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        String cwid = request.getId();
        UUID uuid = UUID.fromString(cwid);
        String name = request.getName();
        double salary = request.getSalary();
        boolean isWinter = request.getIsWinter();
        CW_Type cwType;
        
        IncomeExpensesServiceOuterClass.CommunalWorker.CWType cw_type =
                request.getCwtype();
        IncomeExpensesServiceOuterClass.CommunalWorker.CWType type =
                request.getCwtype();
        if(cw_type== IncomeExpensesServiceOuterClass.CommunalWorker.CWType.GAS) {
            cwType=CW_Type.GAS;
        }
        else if (cw_type== IncomeExpensesServiceOuterClass.CommunalWorker.CWType.WATER)
        {
            cwType =CW_Type.WATER;
        }
        else
        {
            cwType = CW_Type.ELECTRICITY;
        }
        CommunalWorker communalWorker = new CommunalWorker(uuid,name,salary,cwType,isWinter);
        communalWorkerRepository.save(communalWorker);
        IncomeExpensesServiceOuterClass.Empty.Builder response =
                IncomeExpensesServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcCreateWorker(IncomeExpensesServiceOuterClass.CreateCW request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        super.grpcCreateWorker(request, responseObserver);
    }

    @Override
    public void grpcDeleteWorker(IncomeExpensesServiceOuterClass.UUID request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        String id = request.getValue();
        UUID uuid = UUID.fromString(id);
        communalWorkerRepository.deleteById(uuid);
        IncomeExpensesServiceOuterClass.Empty.Builder response =
                IncomeExpensesServiceOuterClass.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAccountant(IncomeExpensesServiceOuterClass.Id request, StreamObserver<IncomeExpensesServiceOuterClass.Accountant> responseObserver) {
       int id = request.getId();
       Optional<Accountant> optionalAccountant = accountantRepository.findById(id);
       Accountant accountant;
        IncomeExpensesServiceOuterClass.Accountant.Builder response  =
                IncomeExpensesServiceOuterClass.Accountant.newBuilder();
        if(optionalAccountant.isPresent())
        {
            accountant = optionalAccountant.get();
            response.setId(accountant.getAccountantId());
            response.setName(accountant.getName());
            response.setSalary(accountant.getSalary());
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void grpcGetAllAccountants(IncomeExpensesServiceOuterClass.Empty request, StreamObserver<IncomeExpensesServiceOuterClass.Accountants> responseObserver) {
       List<Accountant> accountants = accountantRepository.findAll();
        IncomeExpensesServiceOuterClass.Accountants.Builder response = IncomeExpensesServiceOuterClass.Accountants.newBuilder();
        for(Accountant a:accountants)
        {
            IncomeExpensesServiceOuterClass.Accountant.Builder accToList = IncomeExpensesServiceOuterClass.Accountant.newBuilder();
            accToList.setId(a.getAccountantId());
            accToList.setSalary(a.getSalary());
            accToList.setName(a.getName());
            response.addAccountants(accToList);

        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void grpcUpdateAccountantSalary(IncomeExpensesServiceOuterClass.updateAccountantSalary request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        super.grpcUpdateAccountantSalary(request, responseObserver);
    }

    @Override
    public void grpcSetPayPriceForClients(IncomeExpensesServiceOuterClass.Empty request, StreamObserver<IncomeExpensesServiceOuterClass.Empty> responseObserver) {
        super.grpcSetPayPriceForClients(request, responseObserver);
    }
}
