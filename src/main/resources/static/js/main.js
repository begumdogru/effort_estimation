document.addEventListener("DOMContentLoaded", () => {
    const projectForm = document.getElementById("projectForm");
    const taskList = document.getElementById("taskList");
    const projectsList = document.getElementById("projectsList");
    const estimationResult = document.getElementById("estimationResult");

    function addTask() {
        const container = document.createElement("div");
        container.className = "task-container";
        container.innerHTML = `
      <label>Task Adı</label>
      <input type="text" class="taskName" required>
      <label>Açıklama</label>
      <textarea class="description"></textarea>
      <label>Tahmini Efor (saat)</label>
      <input type="number" class="estimatedEffort" step="0.1" required>
    `;
        taskList.appendChild(container);
    }

    window.addTask = addTask; // global scope'a ekle

    projectForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const project = {
            projectName: document.getElementById("projectName").value,
            storyPoint: parseInt(document.getElementById("storyPoint").value),
            category: document.getElementById("category").value,
            complexity: parseFloat(document.getElementById("complexity").value),
            uncertainty: parseFloat(document.getElementById("uncertainty").value),
            riskManagement: document.getElementById("riskManagement").value,
            technology: document.getElementById("technology").value,
            estimatedEffort: 0.0,
            tasks: []
        };

        taskList.querySelectorAll(".task-container").forEach(container => {
            project.tasks.push({
                taskName: container.querySelector(".taskName").value,
                description: container.querySelector(".description").value,
                estimatedEffort: parseFloat(container.querySelector(".estimatedEffort").value),
                actualEffort: parseFloat(container.querySelector(".actualEffort").value),
                technology: container.querySelector(".technology").value,
            });
        });

        fetch("http://localhost:8081/projects/getEstimation", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(project)
        })
            .then(response => response.text())
            .then(estimation => {
                estimationResult.textContent = estimation;
                const effortValue = estimation.match(/\d+(\.\d+)?/);
                project.estimatedEffort = effortValue ? parseFloat(effortValue[0]) : 0.0;

                return fetch("http://localhost:8081/projects/createProject", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(project)
                });
            })
            .then(res => res.json())
            .then(() => {
                alert("Proje başarıyla kaydedildi!");
                loadProjects();
            })
            .catch(error => {
                console.error("Tahmin veya kayıt hatası:", error);
                alert("Hata oluştu.");
            });
    });

    function loadProjects() {
        fetch("http://localhost:8081/projects/getAll"
            , {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            })
            .then(res => res.json())
            .then(data => {
                projectsList.innerHTML = "";
                data.forEach(project => {
                    const div = document.createElement("div");
                    div.className = "project-card";

                    const tasks = Array.isArray(project.tasks) ? project.tasks : [];
                    const taskHTML = tasks.map(task => `
            <li>${task.taskName} - ${task.estimatedEffort} saat</li>
          `).join("");

                    div.innerHTML = `
            <h3>${project.projectName}</h3>
            <p><strong>Kategori:</strong> ${project.category}</p>
            <p><strong>Tahmini Efor:</strong> ${project.estimatedEffort}</p>
            <p><strong>Teknoloji:</strong> ${project.technology}</p>
            <p><strong>Tasklar:</strong></p>
            <ul>${taskHTML}</ul>
          `;

                    projectsList.appendChild(div);
                });
            })
            .catch(err => {
                console.error("Projeleri yükleme hatası:", err);
            });
    }

    loadProjects();
});
