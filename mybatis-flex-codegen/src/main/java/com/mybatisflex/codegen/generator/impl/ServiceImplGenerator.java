/**
 * Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mybatisflex.codegen.generator.impl;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.config.StrategyConfig;
import com.mybatisflex.codegen.constant.TemplateConst;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ServiceImpl 生成器。
 *
 * @author 王帅
 * @since 2023-05-14
 */
public class ServiceImplGenerator implements IGenerator {

    private String templatePath;

    public ServiceImplGenerator() {
        this(TemplateConst.SERVICE_IMPL);
    }

    public ServiceImplGenerator(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        if (!globalConfig.isServiceImplGenerateEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        StrategyConfig strategyConfig = globalConfig.getStrategyConfig();
        
        String serviceImplPackagePath = packageConfig.getServiceImplPackage().replace(".", "/");
        File serviceImplJavaFile = new File(packageConfig.getSourceDir(), serviceImplPackagePath + "/" +
                table.buildServiceImplClassName() + ".java");


        if (serviceImplJavaFile.exists() && !strategyConfig.isOverwriteEnable()) {
            return;
        }


        Map<String, Object> params = new HashMap<>(4);
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("serviceImplConfig", globalConfig.getServiceImplConfig());

        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, serviceImplJavaFile);
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

}
