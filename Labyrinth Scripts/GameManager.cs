using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public GameObject titleScreen;
    public Button startButton;
    public GameObject loseScreen;
    public GameObject winScreen;
    public bool gameActive = false;

   
    void Start()
    {
    }

    void Update()
    {
        
    }

    //starts the game cycle
    
    public void Begin()
    {
        titleScreen.SetActive(false);
        gameActive = true;
    }

    //brings up the lose screen
    public void Lose()
    {
        gameActive = false;
        loseScreen.SetActive(true);
    }
    //brings up the win screen
    public void Win()
    {
        gameActive = false;
        winScreen.SetActive(true);
    }

    //restart game by reloading the scene
    public void RestartGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }

}
